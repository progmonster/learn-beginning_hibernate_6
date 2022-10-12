package learn.beginning_hibernate_6.ch9;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ch9ProductSupplier {
    private final Ch9Product product;

    private final Ch9Supplier supplier;
}
