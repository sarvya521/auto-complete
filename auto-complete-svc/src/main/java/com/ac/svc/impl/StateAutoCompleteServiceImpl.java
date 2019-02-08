package com.ac.svc.impl;

import com.ac.dto.MstState;
import com.ac.model.State;
import com.ac.repo.StateDAO;
import com.ac.svc.AutoCompleteService;
import com.ac.util.AutoCompleteInMemoryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Qualifier("stateAutoCompleteService")
@Service
public class StateAutoCompleteServiceImpl implements AutoCompleteService<State> {

    @Autowired
    private StateDAO stateDAO;

    @Autowired
    private ApplicationContext context;

    private AutoCompleteInMemoryCache<String, List<State>> cache;

    @PostConstruct
    public void initCache() {
        String str_timeToLive, str_timerInterval, str_maxItems;
        str_timeToLive = context.getEnvironment().getProperty("cache.state.timeToLive");
        if(str_timeToLive != null) {
            str_timerInterval = context.getEnvironment().getProperty("cache.state.timerInterval");
            str_maxItems = context.getEnvironment().getProperty("cache.state.maxItems");
        } else {
            str_timeToLive = context.getEnvironment().getProperty("cache.default.timeToLive");
            str_timerInterval = context.getEnvironment().getProperty("cache.default.timerInterval");
            str_maxItems = context.getEnvironment().getProperty("cache.default.maxItems");
        }
        cache = new AutoCompleteInMemoryCache<>(Long.valueOf(str_timeToLive), Long.valueOf(str_timerInterval), Integer.valueOf(str_maxItems));
    }

    @Transactional
    @Override
    public List<State> search(final String key) {
        List<State> result = null;
        if ((result = cache.get(key)) != null) {
            return result;
        }
        List<MstState> states = stateDAO.getStates(key);
        result = states.stream().map(new Function<MstState, State>() {
            @Override
            public State apply(MstState mstState) {
                State state = new State();
                prepareModelFromDto(state, mstState);
                return state;
            }
        }).collect(Collectors.toList());
        cache.put(key, result);
        return result;
    }

    private void prepareModelFromDto(final State stateBean, final MstState stateDto) {
        stateBean.setId(stateDto.getId());
        stateBean.setName(stateDto.getName());
    }
}
