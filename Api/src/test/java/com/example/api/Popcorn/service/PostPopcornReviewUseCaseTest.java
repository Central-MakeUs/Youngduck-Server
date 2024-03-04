package com.example.api.Popcorn.service;

import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcornUser.exceptions.NoPopcornReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PostPopcornReviewUseCaseTest {
    @Mock
    private PopcornAdaptor popcornAdaptor;

    @InjectMocks
    private PostPopcornReviewUseCase useCase;

    @BeforeEach


    @Test
    public void testValidatePopcorn() {
        Long invalidPopcornId = 999L; // Assuming this popcornId doesn't exist
        when(popcornAdaptor.checkIfExists(invalidPopcornId)).thenReturn(Optional.empty());

        assertThrows(NoPopcornReview.class, () -> {
            useCase.validatePopcorn(invalidPopcornId);
        });
    }

}