package com.fastcampus.jpa.bookmanager.domain.listener;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getCreateAt();
    LocalDateTime getUpdateAt();

    void setCreateAt(LocalDateTime localDateTime);
    void setUpdateAt(LocalDateTime localDateTime);
}
