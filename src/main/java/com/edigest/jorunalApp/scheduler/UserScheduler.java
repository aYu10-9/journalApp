package com.edigest.jorunalApp.scheduler;

import com.edigest.jorunalApp.api.response.cache.AppCache;
import com.edigest.jorunalApp.entity.JournalEntry;
import com.edigest.jorunalApp.entity.User;
import com.edigest.jorunalApp.enums.Sentiment;
import com.edigest.jorunalApp.repository.UserRepositoryImpl;
import com.edigest.jorunalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;


//    @Scheduled(cron = "*/10 * * * * *")
    public void fetchUsersAndSendSaMail() {
        System.out.println("--> Scheduler started running...");

        List<User> users = userRepository.getUserForSA();
        System.out.println("--> Fetched users count: " + (users != null ? users.size() : 0));

        if (users == null || users.isEmpty()) {
            System.out.println("--> No users found for Sentiment Analysis.");
            return;
        }

        for (User user : users) {
            System.out.println("--> Processing user: " + user.getEmail());

            List<JournalEntry> journalEntries = user.getJournalEntries();
            if (journalEntries == null || journalEntries.isEmpty()) {
                System.out.println("--> No journal entries found for user: " + user.getEmail());
                continue;
            }

            // 1. Filter entries from the last 7 days and extract non-null sentiments
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate() != null && x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getSentiment)
                    .filter(sentiment -> sentiment != null) // Keep null-safety clean
                    .collect(Collectors.toList());

            System.out.println("--> Filtered sentiments count (last 7 days): " + sentiments.size());

            // 2. Count frequencies of each sentiment
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }

            // 3. Find the most frequent sentiment
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            // 4. Send the email if a sentiment pattern was identified
            if (mostFrequentSentiment != null) {
                System.out.println("--> Sending email to: " + user.getEmail() + " | Sentiment: " + mostFrequentSentiment);
                emailService.sendEmail(
                        user.getEmail(),
                        "Sentiment for last 7 days",
                        "Your dominant sentiment over the last 7 days was: " + mostFrequentSentiment.toString()
                );
            } else {
                System.out.println("--> No frequent sentiment found for user: " + user.getEmail() + " (Possibly no entries in the last 7 days)");
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}