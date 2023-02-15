package dev.crius.cquest.utils;

import dev.crius.cquest.CQuest;
import dev.crius.cquest.placeholder.Placeholder;
import dev.crius.cquest.placeholder.PlaceholderBuilder;
import org.bukkit.ChatColor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static String format(String content) {
        return colorize(processPlaceholders(content, new PlaceholderBuilder().build()));
    }

    public static List<String> format(List<String> content) {
        return colorize(processPlaceholders(content, new PlaceholderBuilder().build()));
    }

    public static String format(String content, List<Placeholder> placeholders) {
        return colorize(processPlaceholders(content, placeholders));
    }

    public static List<String> format(List<String> content, List<Placeholder> placeholders) {
        return colorize(processPlaceholders(content, placeholders));
    }

    public static List<String> processPlaceholders(List<String> contents, List<Placeholder> placeholders) {
        return contents.stream().map(content -> processPlaceholders(content, placeholders))
                .collect(Collectors.toList());
    }

    public static String processPlaceholders(String content, List<Placeholder> placeholders) {
        String processedContent = content;
        for (Placeholder placeholder : placeholders) {
            processedContent = placeholder.process(processedContent);
        }
        return processedContent;
    }

    public static String colorize(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    public static List<String> colorize(List<String> content) {
        List<String> lore = new ArrayList<>();
        content.forEach(line -> {
           lore.add(colorize(line));
        });
        return lore;
    }

/*    public static String formatDuration(String format, Duration duration) {
        long hoursInt = duration.toHours();
        long minutesInt = duration.toMinutes() % 60;
        long secondsInt = duration.getSeconds() % 60;

        String hours = CQuest.getInstance().getConfiguration().placeholders.hours;
        String minutes = CQuest.getInstance().getConfiguration().placeholders.minutes;
        String seconds = CQuest.getInstance().getConfiguration().placeholders.seconds;

        if(hoursInt == 0) {
            if(minutesInt == 0) {
                return 
            }
        }
        return format.replace("%hours%", hoursInt != 0 ? hours.replace("%hours_int%", hours) : "")
        .replace("%minutes%", minutesInt != 0 ? minutes.replace("%minutes_int%", minutes) : "")
        .replace("%seconds%", secondsInt != 0 ? seconds.replace("%seconds_int%", seconds) : "");
    }*/
}
