package com.zooplus.sdc.converter.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ResourcesAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unsecuredResourceAccess() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void securedResourceAccess() throws Exception {
        mockMvc.perform(get("/secured"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(containsString("Access Denied")))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    public void authenticatedSecuredResourceAccess() throws Exception {
        mockMvc.perform(get("/secured"))
                .andExpect(status().isOk());
    }
}
