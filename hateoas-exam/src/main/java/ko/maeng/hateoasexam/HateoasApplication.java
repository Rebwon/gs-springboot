package ko.maeng.hateoasexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;

@SpringBootApplication
public class HateoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HateoasApplication.class, args);
	}

	// SimpleIdentifiableRepresentationModelAssembler에서 controllerClass만 주입받는 생성자에서
	// EvoInflectorLinkRelationProvider로 대체될 수 있도록 빈을 정의.
	// 복수의 리소스 유형이 포함된 컬렉션을 포맷함.
//	@Bean
//	EvoInflectorLinkRelationProvider relProvider() {
//		return new EvoInflectorLinkRelationProvider();
//	}
}
