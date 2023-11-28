import { FC, useState } from "react";
import { Post } from "../common/commontypes";
import {
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  Divider,
  Typography,
} from "@mui/material";
import CommentList from "../comments/CommentList";

interface Props {
  post: Post;
}

const PostListItem: FC<Props> = ({ post }) => {
  const [showComment, setShowComment] = useState(false);

  return (
    <Card sx={{ width: 500, mb: 5 }}>
      <CardActionArea>
        <CardContent>
          <Typography gutterBottom variant='subtitle1' component={"div"}>
            {post.title}
          </Typography>
          <Typography variant='body2' color='text.secondary'>
            {post.description}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions sx={{ justifyContent: "space-around", ml: 1 }}>
        <Typography variant='subtitle1'>
          Created by: {post.author.name}
        </Typography>
        <Button
          onClick={() => setShowComment((prevValue) => !prevValue)}
          size='small'
          color='secondary'
          variant='contained'
        >
          {!showComment ? 'Show comments': "Hide comments"} 
        </Button>
      </CardActions>
      {showComment && (
        <>
          <Divider />
          <CommentList comments={post.comments} />
        </>
      )}
    </Card>
  );
};

export default PostListItem;
