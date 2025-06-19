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
    private final IDService mockID = mock(IDService.class);
    private final WatchlistService mockService = mock(WatchlistService.class);

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
    private final WatchlistItemDTO itemDTO = new WatchlistItemDTO("jon@doe.io", product);
    private final WatchlistItem item = new WatchlistItem("123", "jon@doe.io", product);
    private static final String SAVED_ITEM_SUCCESSFULLY = "Item was saved successfully.";

    @Test
    void addWatchlistItem_shouldReturn200_whenGetItem() {
        // GIVEN
        mockRepo.save(item);
        // WHEN
        when(mockID.createID()).thenReturn("123");
        when(mockRepo.save(item)).thenReturn(item);
        when(mockService.addWatchlistItem(itemDTO)).thenReturn(ResponseEntity.ok(SAVED_ITEM_SUCCESSFULLY));
        ResponseEntity<String> actual = mockService.addWatchlistItem(itemDTO);
        String id = mockID.createID();
        // THEN
        assertEquals(ResponseEntity.ok(SAVED_ITEM_SUCCESSFULLY), actual);
        assertEquals("123", id);
        verify(mockRepo, times(1)).save(item);
    }
}
