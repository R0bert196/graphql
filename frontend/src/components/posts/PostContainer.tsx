import { Alert, Box, Button } from "@mui/material";
import { useGetPostsQuery } from "../../generated/graphql";
import Loader from "../common/Loader";
import styled from "@emotion/styled";
import { Post } from "../common/commontypes";
import PostList from "./PostList";

const PostContainer = () => {
  const { data, loading, error, refetch } = useGetPostsQuery({
    fetchPolicy: "network-only",
  });

    const RetryButton = styled(Button)({
        display: 'block',
        marginTop: '10px'
    })
    
  return (
    <Box sx={{ display: "flex", justifyContent: "center" }}>
      <Loader open={loading} />
      {data && <PostList posts={data.getPosts as Post[]} />}
      {error && (
        <Alert sx={{ marginTop: 2, width: "50%" }} severity='error'>
          {error.message}, please try again
          <RetryButton
            variant='contained'
            color='error'
            onClick={() => {
                console.log("retry");
                refetch();
            }}
          >
            Retry
          </RetryButton>
        </Alert>
      )}
    </Box>
  );
};

export default PostContainer;
