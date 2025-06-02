package com.duikt.app.service.impl;

import com.duikt.app.domain.ProgressTrackerDTO;
import com.duikt.app.entity.ProgressTrackerEntity;
import com.duikt.app.entity.UserEntity;
import com.duikt.app.exception.ProgressTrackerNotFoundException;
import com.duikt.app.exception.UserNotFoundException;
import com.duikt.app.mapper.ProgressTrackerMapper;
import com.duikt.app.repository.ProgressTrackerRepository;
import com.duikt.app.repository.UserRepository;
import com.duikt.app.service.ProgressTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressTrackerServiceImpl implements ProgressTrackerService {

    private final ProgressTrackerRepository progressTrackerRepository;
    private final UserRepository userRepository;
    private final ProgressTrackerMapper progressTrackerMapper;

    @Transactional(readOnly = true)
    public List<ProgressTrackerDTO> findAllProgressEntries() {
        return progressTrackerMapper.toProgressDtoList(progressTrackerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProgressTrackerDTO findProgressEntryById(UUID id) {
        return progressTrackerMapper.toProgressDto(progressTrackerRepository.findById(id)
                .orElseThrow(() -> new ProgressTrackerNotFoundException("Progress entry not found: " + id)));
    }

    @Override
    @Transactional
    public ProgressTrackerDTO createProgressEntry(ProgressTrackerDTO progressTrackerDTO, UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        ProgressTrackerEntity progressEntry = progressTrackerMapper.toProgressEntity(progressTrackerDTO)
                .toBuilder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return progressTrackerMapper.toProgressDto(progressTrackerRepository.save(progressEntry));
    }

    @Override
    @Transactional
    public ProgressTrackerDTO updateProgressEntry(UUID id, ProgressTrackerDTO updatedProgressEntry) {
        ProgressTrackerEntity existingProgressEntry = progressTrackerRepository.findById(id)
                .orElseThrow(() -> new ProgressTrackerNotFoundException("Progress entry not found with id: " + id));

        ProgressTrackerEntity progressEntryWithUpdates = existingProgressEntry.toBuilder()
                .date(updatedProgressEntry.getDate())
                .weight(updatedProgressEntry.getWeight())
                .fatPercentage(updatedProgressEntry.getFatPercentage())
                .muscleMass(updatedProgressEntry.getMuscleMass())
                .build();

        return progressTrackerMapper.toProgressDto(progressTrackerRepository.save(progressEntryWithUpdates));
    }

    @Override
    @Transactional
    public void deleteProgressEntry(UUID id) {
        if (!progressTrackerRepository.existsById(id)) {
            throw new ProgressTrackerNotFoundException("Progress entry not found with id: " + id);
        }
        progressTrackerRepository.deleteById(id);
    }
}

