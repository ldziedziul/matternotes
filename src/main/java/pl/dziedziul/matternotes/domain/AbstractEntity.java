package pl.dziedziul.matternotes.domain;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners({ AuditingEntityListener.class })
@MappedSuperclass
public class AbstractEntity implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
