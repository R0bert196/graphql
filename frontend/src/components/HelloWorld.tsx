import { gql, useQuery } from "@apollo/client";
import { useHelloWorldQuery } from "../generated/graphql";


const HelloWorld = () => {
  const { data, error, loading } = useHelloWorldQuery();

  return (
    <>
      {loading && <div>Loading...</div>}
      {error && <div>Error</div>}
      {data && <div>{data.helloWorld}</div>}
    </>
  );
};

export default HelloWorld;
