package com.ieatfood1234;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://prices.runescape.wiki/api/v1/osrs/latest"))
                .header("User-Agent", "osrs-price-tracker/1.0 (discord: aether6765)")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(response.body(), Map.class);
        Map<String, Object> data = (Map<String, Object>) jsonMap.get("data");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the item id: ");
        String itemId = scanner.nextLine();

        if (data.containsKey(itemId)) {
            Map<String, Object> itemData = (Map<String, Object>) data.get(itemId);
            int highPrice = (int) itemData.get("high");
            int lowPrice = (int) itemData.get("low");
            System.out.println("High Price: " + highPrice);
            System.out.println("Low Price: " + lowPrice);
        }
        else {
            System.out.println("Item not found.");
        }
    }
}