package com.glody.glody_platform.partner.service;

import com.glody.glody_platform.partner.dto.PartnerClickDto;
import com.glody.glody_platform.partner.entity.Partner;
import com.glody.glody_platform.partner.entity.PartnerClick;
import com.glody.glody_platform.partner.repository.PartnerClickRepository;
import com.glody.glody_platform.partner.repository.PartnerRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerClickService {

    private final PartnerClickRepository clickRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;

    public PartnerClick logClick(PartnerClickDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Partner partner = partnerRepository.findById(dto.getPartnerId()).orElseThrow();

        PartnerClick click = new PartnerClick();
        click.setUser(user);
        click.setPartner(partner);
        return clickRepository.save(click);
    }
}
