#a command to create the pact file and save it to the pacts folder
#consumer tests in the src/test/java/dev/terralab/blog/examples/pactquestdemo/contract/consumer, we need to run these tests to create the pact file
#run only these tests
create-pact:
	mvn clean test -Dtest=dev.terralab.blog.examples.pactquestdemo.contract.consumer.QuestManagerApiConsumerPactTest
	mkdir -p pacts
	cp target/pacts/QuestManagerApiClient-QuestManagerApi.json pacts/QuestManagerApiClient-QuestManagerApi.json

test: create-pact
	mvn clean test -Dtest=!dev.terralab.blog.examples.pactquestdemo.contract.consumer.QuestManagerApiConsumerPactTest
	

build: test
	mvn clean install -DskipTests
