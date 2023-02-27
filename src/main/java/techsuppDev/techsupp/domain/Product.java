package techsuppDev.techsupp.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@DiscriminatorValue("i")
@AllArgsConstructor
@Entity
@Builder
//매핑 다시해야함
@SqlResultSetMapping(
        name = "ProductMapping",
        classes = @ConstructorResult(
                targetClass = Product.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "seqId", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "information", type = String.class),
                        @ColumnResult(name = "totalPrice", type = int.class),
                        @ColumnResult(name = "investPrice", type = int.class),
                        @ColumnResult(name = "period", type = LocalDateTime.class),
                        @ColumnResult(name = "status", type = int.class),
                        @ColumnResult(name = "createDate", type = LocalDateTime.class),
                        @ColumnResult(name = "clickCount", type = int.class)
                })
)
@Table(name = "Product")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long seqId;
    @Column(nullable = false)
    private String name;

    @Column(length = 2000, nullable = false)
    private String information;
    @Column(nullable = false)
    private int totalPrice;
    @Column(nullable = false)
    private int investPrice;
//    개인 투자액 컬럼 추가
    private LocalDate period;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int clickCount;
//    모집인원 컬럼 추가
//    투자율 삭제
}
