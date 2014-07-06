package com.itrane.mvcdemo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * エンティティのスーパークラス.
  * 　・Serializable の実装
  * 　・Id フィールド（自動設定）
  * 　・version フィールド
 */
@MappedSuperclass
public abstract class AbstractPersistentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id = null;
    protected Integer version = 0;

    @Version
    @Column(name="OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
