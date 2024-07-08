package com.example.softproject1.repository;
import com.example.softproject1.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
