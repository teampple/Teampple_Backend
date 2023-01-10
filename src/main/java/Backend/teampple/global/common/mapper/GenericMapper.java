package Backend.teampple.global.common.mapper;

public interface GenericMapper<D, E> {
    D toDto(E e);

    E toEntity(D d);
}
