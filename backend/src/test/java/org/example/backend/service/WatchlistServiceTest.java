package org.example.backend.service;

import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

import org.example.backend.model.Category;
import org.example.backend.model.Currency;
import org.example.backend.model.Dimension;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.Images;
import org.example.backend.model.Material;
import org.example.backend.model.Measure;
import org.example.backend.model.Product;
import org.example.backend.model.ProductFeatures;
import org.example.backend.model.Unit;
import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.repository.WatchlistRepo;


public class WatchlistServiceTest {
    private final WatchlistRepo mockRepo = mock(WatchlistRepo.class);
    private final IDService mockUUID = mock(IDService.class);
    private final WatchlistService mockService = mock(WatchlistService.class);
    private final WatchlistService watchlistService = new WatchlistService(mockRepo, mockUUID);

    private final Measure width = new Measure(
                                    49,
                                    Unit.CM
                                );
    private final Measure length = new Measure(
                                    45,
                                    Unit.CM
                                );
    private final Measure height = new Measure(
                                    78,
                                    Unit.CM
                                );
    private final ProductFeatures feat1 = new ProductFeatures(
                        new Dimension(width, length, height),
                        new Measure(6, Unit.KG),
                        List.of(Material.OAK, Material.ASH),
                        List.of("oak", "ash", "black")
                    );          
    private final Product product = 
            Product.builder()
                .id("1536716")
                .number("1990")
                .name("Lara Chair")
                .manufacturer("Erol")
                .category(Category.FURNITURE)
                .group(Group.SEATING)
                .family(Family.CHAIR)
                .features(feat1)
                .info("Info")
                .description("Description")
                .images(new Images(
                        List.of("/public/small/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"),
                        List.of("http://imag)e2.test"),
                        List.of("/public/large/lara/Lara--1990--chair--cutoutAngle-2--Ash--)M.jpg")
                    )
                )
                .price(BigDecimal.valueOf(370))
                .currency(Currency.GBP)
                .amount(100)
                .build();
    private final String id = "123";
    private final String email = "jon@doe.io";
    // // private final WatchlistItemDTO itemDTO = new WatchlistItemDTO(email, product, List.of());
    private final WatchlistItem item = new WatchlistItem(id, email, product);
    private static final String SAVED_ITEM_SUCCESSFULLY = "Item was saved successfully.";

    @Test
    void getWatchlistItems_shouldReturnItems_whenGetEmail() {
        // GIVEN
        List<WatchlistItem> expected = List.of(item);
        mockRepo.save(item);
        // WHEN
        when(mockRepo.findAllByUserEmail(email)).thenReturn(expected);
        List<WatchlistItem> actual = watchlistService.getWatchlistItems(email);
        //THEN
        verify(mockRepo).findAllByUserEmail(email);
        assertEquals(expected, actual);
    }

    @Test
    void getWatchlistItems_shouldReturnEmptyList_whenNoItemFound() {
        // GIVEN
        List<WatchlistItem> expected = List.of();
        // WHEN
        when(mockRepo.findAllByUserEmail(email)).thenReturn(expected);
        List<WatchlistItem> actual = watchlistService.getWatchlistItems(email);
        //THEN
        verify(mockRepo).findAllByUserEmail(email);
        assertEquals(expected, actual);
    }

    // @Test
    // void addWatchlistItem_shouldthrowOk_whenGetItem() {
    //     // WHEN
    //     when(mockUUID.createID()).thenReturn(id);
    //     when(mockRepo.save(item)).thenReturn(item);
    //     // when(mockService.addWatchlistItem(itemDTO)).thenReturn(item);
    //     WatchlistItem actual = watchlistService.addWatchlistItem(itemDTO);
    //     String mockID = mockUUID.createID();
    //     // THEN
    //     assertEquals(item, actual);
    //     assertEquals(id, mockID);
    //     verify(mockRepo, times(1)).save(item);
    // }

    @Test
    void deleteWatchlistItem_shouldDeleteItem_whenGetID() {
        // WHEN
        watchlistService.deleteWatchlistItem(id);
        // THEN
        verify(mockRepo, times(1)).deleteById(id);
    }
}
