package techsuppDev.techsupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import techsuppDev.techsupp.DTO.PageRequestDTO;
import techsuppDev.techsupp.DTO.PageResultDTO;
import techsuppDev.techsupp.DTO.ProductDTO;
import techsuppDev.techsupp.domain.Product;
import techsuppDev.techsupp.repository.AdminProductRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    private final AdminProductRepository adminProductRepository;

    @Override
    public Long register(ProductDTO productDTO) {
        Product entity = dtoToEntity(productDTO);
        adminProductRepository.save(entity);

        return entity.getId();
    }

    @Override
    public PageResultDTO<ProductDTO, Product> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Page<Product> result = adminProductRepository.findAll(pageable);
        Function<Product, ProductDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
