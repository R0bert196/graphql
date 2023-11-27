import {
  ApolloClient,
  ApolloLink,
  HttpLink,
  InMemoryCache,
  concat,
} from "@apollo/client";

const httpLink = new HttpLink({
  uri: "http://localhost:8080/graphql",
});

const authMiddleware = new ApolloLink((operation, forward) => {
  localStorage.getItem("token2") &&
    operation.setContext(({ headers = {} } = {}) => ({
      headers: {
        ...headers,
        Authorization: localStorage.getItem("token2"),
      },
    }));

  return forward(operation);
});

const client = new ApolloClient({
    cache: new InMemoryCache(),
    link: concat(authMiddleware, httpLink)
});

export default client;
