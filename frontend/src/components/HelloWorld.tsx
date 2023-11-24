import { gql, useQuery } from "@apollo/client";
import { HelloWorldDocument } from "../gql/graphql";

const HELLO_WORLD_QUERY = gql`
  query  HelloWorld {
    helloWorld
  }
`;

const HelloWorld = () => {
  const { data, error, loading } = useQuery(HelloWorldDocument);

  return (
    <>
      {loading && <div>Loading...</div>}
      {error && <div>Error</div>}
      {data && <div>{data.helloWorld}</div>}
    </>
  );
};

export default HelloWorld;
