import { Alert, Box, Button, TextField, Typography } from "@mui/material";
import Loader from "../common/Loader";
import * as yup from "yup";
import { useFormik } from "formik";
import { useAddPostMutation } from "../../generated/graphql";
import { useNavigate } from "react-router-dom";

const validationSchema = yup.object({
  title: yup.string().required("Please enter post title"),
  description: yup.string().required("Please enter post description"),
});

const AddPost = () => {
  const navigate = useNavigate();

  const [addPost, { loading, error }] = useAddPostMutation({
    fetchPolicy: "network-only",
  });

  const postForm = useFormik({
    initialValues: {
      title: "",
      description: "",
    },
    validationSchema,
    onSubmit: (values) => {
      console.log(values);
      addPost({
        variables: {
          title: values.title,
          description: values.description,
        },
      }).then(() => {
        navigate("/posts");
      });
    },
  });

  return (
    <Box sx={{ textAlign: "center", marginTop: "2rem" }}>
      <Typography variant='h6'>Add post</Typography>
      <Box sx={{ display: "flex", justifyContent: "center" }}>
        <form
          onSubmit={postForm.handleSubmit}
          style={{
            display: "flex",
            flexDirection: "column",
            width: "30%",
            gap: "1rem",
          }}
        >
          <TextField
            id='title'
            name='title'
            placeholder='Enter post title'
            label='Title'
            sx={{ marginTop: "10px" }}
            variant='outlined'
            value={postForm.values.title}
            onChange={postForm.handleChange}
            onBlur={postForm.handleBlur}
            error={postForm.touched.title && Boolean(postForm.errors.title)}
            helperText={postForm.errors.title}
          />
          <TextField
            id='description'
            name='description'
            placeholder='Enter post description'
            label='Description'
            sx={{ marginTop: "10px" }}
            variant='outlined'
            value={postForm.values.description}
            onChange={postForm.handleChange}
            onBlur={postForm.handleBlur}
            error={
              postForm.touched.description &&
              Boolean(postForm.errors.description)
            }
            helperText={postForm.errors.description}
          />
          <Button variant='contained' type='submit'>
            Add Post
          </Button>
          <Loader open={loading} />
          {error && (
            <Alert severity='error'>{error.message}, please try again.</Alert>
          )}
        </form>
      </Box>
    </Box>
  );
};

export default AddPost;
