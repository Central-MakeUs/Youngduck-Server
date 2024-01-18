package com.example.domains.quit.service;

import com.example.adaptor.DomainService;
import com.example.domains.quit.adaptor.QuitAdaptor;
import com.example.domains.quit.domain.Quit;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class QuitDomainService {
    private final QuitAdaptor quitReasonAdaptor;

    public void save(Quit quitReason) {
        quitReasonAdaptor.save(quitReason);
    }
}
