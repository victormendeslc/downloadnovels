package org.download.novels.pojo;

import lombok.Getter;
import lombok.Setter;
import org.download.novels.enums.TypeSite;

import java.util.Objects;

@Getter
@Setter
public class Website implements  Comparable<Website>{

    private String id;
    private String name;

    public Website(TypeSite typeSite) {
        this.id = typeSite.name();
        this.name = typeSite.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Website website = (Website) o;
        return Objects.equals(id, website.id) && Objects.equals(name, website.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public int compareTo(Website o) {
        return this.id.compareTo(o.getId());
    }
}
