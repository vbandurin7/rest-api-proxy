package com.vk.restapiproxy.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void hasAccessEverywhere() throws Exception {
        List<String> accessibleEndpoints = List.of(
                "http://localhost:8080/api/posts", "http://localhost:8080/api/posts/1",
                "http://localhost:8080/api/users", "http://localhost:8080/api/users/1",
                "http://localhost:8080/api/albums", "http://localhost:8080/api/albums/1"
        );
        for (String accessibleEndpoint : accessibleEndpoints) {
            mvc.perform(MockMvcRequestBuilders.get(accessibleEndpoint))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Test
    @WithMockUser(roles = "POSTS_VIEWER")
    void onlyPostsGet() throws Exception {
        List<String> accessibleEndpoints = List.of(
                "http://localhost:8080/api/posts", "http://localhost:8080/api/posts/1");
        List<String> nonAccessibleEndpoints = List.of(
                "http://localhost:8080/api/users", "http://localhost:8080/api/albums");

        for (String accessibleEndpoint : accessibleEndpoints) {
            checkViewerRequests(accessibleEndpoint);
        }

        for (String nonAccessibleEndpoint : nonAccessibleEndpoints) {
            mvc.perform(MockMvcRequestBuilders.get(nonAccessibleEndpoint))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }
    }

    @Test
    @WithMockUser(roles = "POSTS_EDITOR")
    void onlyPosts() throws Exception {
        List<String> accessibleEndpoints = List.of(
                 "http://localhost:8080/api/posts");

        List<String> nonAccessibleEndpoints = List.of(
                "http://localhost:8080/api/users", "http://localhost:8080/api/albums");
        String addRequestBody = "{\"title\": \"post title\", \"body\": \"post body\", \"userId\": 1}";
        String updateRequestBody = "{\"title\": \"post title\", \"body\": \"post body\", \"userId\": 1, \"id\": 1}";

        for (String accessibleEndpoint : accessibleEndpoints) {

            // GET
            mvc.perform(MockMvcRequestBuilders.get(accessibleEndpoint))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            // POST
            mvc.perform(MockMvcRequestBuilders.post(accessibleEndpoint)
                            .content(addRequestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            // PUT
            mvc.perform(MockMvcRequestBuilders.put(accessibleEndpoint + "/1")
                            .content(updateRequestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            // DELETE
            mvc.perform(MockMvcRequestBuilders.delete(accessibleEndpoint + "/4"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }
        for (String nonAccessibleEndpoint : nonAccessibleEndpoints) {
            mvc.perform(MockMvcRequestBuilders.get(nonAccessibleEndpoint))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }
    }


    private void checkViewerRequests(String accessibleEndpoint) throws Exception {
        // GET
        mvc.perform(MockMvcRequestBuilders.get(accessibleEndpoint))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // POST
        mvc.perform(MockMvcRequestBuilders.post(accessibleEndpoint))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // PUT
        mvc.perform(MockMvcRequestBuilders.put(accessibleEndpoint))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // DELETE
        mvc.perform(MockMvcRequestBuilders.post(accessibleEndpoint))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
