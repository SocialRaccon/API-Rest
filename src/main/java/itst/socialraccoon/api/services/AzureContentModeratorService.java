package itst.socialraccoon.api.services;

import com.azure.ai.contentsafety.ContentSafetyClient;
import com.azure.ai.contentsafety.ContentSafetyClientBuilder;
import com.azure.ai.contentsafety.models.AnalyzeImageOptions;
import com.azure.ai.contentsafety.models.AnalyzeImageResult;
import com.azure.ai.contentsafety.models.ContentSafetyImageData;
import com.azure.core.credential.KeyCredential;
import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class AzureContentModeratorService {

    private final ContentSafetyClient contentSafetyClient;

    public AzureContentModeratorService(
            @Value("${azure.content-safety.endpoint}") String endpoint,
            @Value("${azure.content-safety.key}") String key) {
        this.contentSafetyClient = new ContentSafetyClientBuilder()
                .credential(new KeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }

    public boolean isImageSafe(MultipartFile file) {
        try {
            ContentSafetyImageData image = new ContentSafetyImageData();
            image.setContent(BinaryData.fromBytes(file.getBytes()));

            AnalyzeImageResult response = contentSafetyClient.analyzeImage(
                    new AnalyzeImageOptions(image));
            System.out.println(response.getCategoriesAnalysis());
            return response.getCategoriesAnalysis().stream()
                    .noneMatch(result -> result.getSeverity() > 4);
        } catch (Exception e) {
            throw new RuntimeException("Failed to analyze image", e);
        }
    }
}