package com.codename1.demos.netflixclone.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, String> {
}
