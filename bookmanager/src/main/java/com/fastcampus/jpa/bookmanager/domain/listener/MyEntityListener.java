package com.fastcampus.jpa.bookmanager.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MyEntityListener {
    //리스너에서 하나만 구현하여도 주입하여 사용하므로 반복코드가 줄어들게 된다.
        //여러 엔티티에서 사용하는 엔티티리스너 createAt과 updateAt이 존재한다는 것을 알아야 한다.
    @PrePersist
    public void prePersist(Object o){ //object 파라미터를 받아야한다. 어떤 값인지 알수 없다. 해당 엔티티를 받아서 처리해야 한다. object 가 어떤타입인지 알기 어려움
        if(o instanceof Auditable){
            ((Auditable) o).setCreateAt(LocalDateTime.now());
            ((Auditable) o).setUpdateAt(LocalDateTime.now());
        }

    }

    @PreUpdate
    public void preUpdate(Object o){
        if(o instanceof Auditable){
            ((Auditable) o).setUpdateAt(LocalDateTime.now());
        }
    }

}
