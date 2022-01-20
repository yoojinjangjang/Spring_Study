package com.example.restaurant.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryifs<T>{

    private final List<T> db = new ArrayList<>();
    private  int index = 0;

    @Override
    public Optional<T> findById(int index) {

        return db.stream().filter(it->it.getIndex() == index).findFirst();
    }

    @Override
    public T save(T entity) {

        var optionalEntity   = db.stream().filter(it->it.getIndex()==entity.getIndex()).findFirst(); // 동일시 이미 있음, 없으면 없는 케이스

        if(optionalEntity.isEmpty()){        //db 에 없는 경우

            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;

        }else{  //db 에 이미 있을때
            var preIndex = optionalEntity.get().getIndex(); //이미 있던 데이터 인덱스
            entity.setIndex(preIndex);


            deleteById(preIndex); //기존 entity 삭제
            db.add(entity);
            return entity;
        }


    }

    @Override
    public void deleteById(int index) {

        var optionalEntity = db.stream().filter(it->it.getIndex()==index).findFirst();

        if(optionalEntity.isPresent()){
            db.remove(optionalEntity.get());
        }
    }

    @Override
    public List<T> findAll() {
        return db;
    }
}
