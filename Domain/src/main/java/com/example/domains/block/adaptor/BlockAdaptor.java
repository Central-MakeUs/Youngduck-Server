package com.example.domains.block.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.block.entity.Block;
import com.example.domains.block.exception.exceptions.DuplicateBlockRequest;
import com.example.domains.block.repository.BlockRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class BlockAdaptor {
    private final BlockRepository blockRepository;
    public void save(Long userId,Long reportedUserId, Long reviewId){
        validateUser(userId,reviewId);
        final Block result = Block.of(userId,reportedUserId,reviewId);
        blockRepository.save(result);
    }

    private void validateUser(Long userId, Long reviewId) {
        if(blockRepository.existsByUserIdAndScreeningReviewId(userId,reviewId)) {
            throw DuplicateBlockRequest.EXCEPTION;
        }
    }
}
