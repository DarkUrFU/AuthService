package com.darkurfu.authservice.datamodels;


import com.darkurfu.authservice.service.system.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity(name = "bans")
@AllArgsConstructor
public class Ban {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Timestamp date;

    public Ban(){
        this.id = UUID.randomUUID();
        this.date = TimeUtil.getCurrentTime();
    }

    public Ban(UUID userId, String description){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.description = description;
        this.date = TimeUtil.getCurrentTime();
    }

}
