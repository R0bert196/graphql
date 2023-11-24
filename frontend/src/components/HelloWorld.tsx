import { gql, useQuery } from "@apollo/client";

const HELLO_WORLD_QUERY = gql`
  query {
    helloWorld
  }
`;

const HelloWorld = () => {
  const { data, error, loading } = useQuery(HELLO_WORLD_QUERY);

  return (
    <>
      {loading && <div>Loading...</div>}
      {error && <div>Error</div>}
      {data && <div>{data.helloWorld}</div>}
    </>
  );
};

export default HelloWorld;
