package com.example.domains.quit.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.quit.domain.Quit;
import com.example.domains.quit.repository.QuitRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class QuitAdaptor {
    private final QuitRepository quitReasonRepository;

    public void save(Quit quitReason) {
        quitReasonRepository.save(quitReason);
    }
}