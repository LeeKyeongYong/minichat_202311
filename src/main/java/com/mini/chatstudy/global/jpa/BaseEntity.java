package com.mini.chatstudy.global.jpa;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static lombok.AccessLevel.PROTECTED;
import static jakarta.persistence.GenerationType.IDENTITY;
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @EqualsAndHashCode.Include;
    private Long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Transient // 아래의 필드가 DB필드가 되는것을 방지하기위함
    @Builder.Default
    private Map<String,Object> extra = new LinkedHashMap<>();

    public String etModelName(){
        String simpleName = this.getClass().getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0))+simpleName.substring(1);
    }

}
