package com.example.docs.app.v1.order;

import com.example.docs.app.v1.order.request.ProblemRequest;
import com.example.docs.app.v1.order.request.ProblemSaveRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "B_PROBLEM")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @Column(name = "cnt", length = 11)
    Integer cnt;

    @Column(name = "name", length = 100)
    String name;

    static public Problem createOrder(ProblemSaveRequest req) {
        return Problem.builder().cnt(req.getCnt()).name(req.getName()).build();
    }
}
