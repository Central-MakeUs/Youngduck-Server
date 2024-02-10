package com.example.domains.block.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.block.entity.Block;
import com.example.domains.block.exception.exceptions.DuplicateBlockRequest;
import com.example.domains.block.repository.BlockRepository;
import com.example.fcm.adaptor.FcmTokenAdaptor;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class BlockAdaptor {
    private final BlockRepository blockRepository;
    private final FcmTokenAdaptor fcmTokenAdaptor;
    public void save(Long userId,Long reportedUserId, Long reviewId,Long popcornReviewId){
        validateUser(userId,reviewId,popcornReviewId);
        final Block result = Block.of(userId,reportedUserId,reviewId,popcornReviewId);
        blockRepository.save(result);
        fcmTokenAdaptor.execute(userId);
    }

    private void validateUser(Long userId, Long reviewId,Long popcornReviewId) {
        if (reviewId == null) {
            if(blockRepository.existsByUserIdAndPopcornReviewId(userId,popcornReviewId)) {
                throw DuplicateBlockRequest.EXCEPTION;
            }
        } else if(popcornReviewId==null) {
            if(blockRepository.existsByUserIdAndScreeningReviewId(userId,reviewId)) {
                throw DuplicateBlockRequest.EXCEPTION;
            }
        }

    }
}
