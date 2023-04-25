package pl.gm.bankapi.communication.feedback.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.communication.feedback.ContactFeedbackDto;
import pl.gm.bankapi.communication.feedback.model.ContactFeedbackEntity;
import pl.gm.bankapi.communication.feedback.repository.FeedBackRepository;

@Service
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final ModelMapper modelMapper;

    public FeedBackService(FeedBackRepository feedBackRepository, ModelMapper modelMapper) {
        this.feedBackRepository = feedBackRepository;
        this.modelMapper = modelMapper;
    }

    public void saveFeedback(ContactFeedbackDto contactFeedbackDto) {
        ContactFeedbackEntity feedback = modelMapper.map(contactFeedbackDto, ContactFeedbackEntity.class);
        feedBackRepository.save(feedback);
    }
}
