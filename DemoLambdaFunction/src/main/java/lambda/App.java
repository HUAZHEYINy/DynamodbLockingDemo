package lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dynamodb.LockingDemoObj;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<Object, Object> {

    public Object handleRequest(final Object input, final Context context) {
        System.out.println("Start processing!");

        AmazonDynamoDB dynamoDBClient =
                AmazonDynamoDBClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1)
                        .build();

        System.out.println("After building client!");

        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);

        dynamoDBMapper.save(generateLockingDemoObj());

        dynamoDBClient.shutdown();

        System.out.println("End processing!");
        return null;
    }

    public LockingDemoObj generateLockingDemoObj() {
        LockingDemoObj lockingDemoObj = new LockingDemoObj();
        lockingDemoObj.setHashKey("Hash_1");
        lockingDemoObj.setSortKey("Sort_1");
        lockingDemoObj.setData("Data_1");
        return lockingDemoObj;
    }
}
