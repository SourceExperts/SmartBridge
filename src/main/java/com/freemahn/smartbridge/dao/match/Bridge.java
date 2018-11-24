package com.freemahn.smartbridge.dao.match;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "bridge"
    //    ,
    //    uniqueConstraints = {@UniqueConstraint(columnNames = {"corporate_id", "startup_id", "name"})
    //    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bridge
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "corporate_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Corporate corporate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "startup_id")

    @OnDelete(action = OnDeleteAction.CASCADE)
    Startup matchedStartup;
    private final LocalDateTime createdAt = LocalDateTime.now();

    String name;
    String description;


}

