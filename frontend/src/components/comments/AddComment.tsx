import { Box, Button, TextField } from "@mui/material";
import { useFormik } from "formik";
import { FC } from "react";
import * as yup from "yup";

interface Props {
  onSubmit: (values: string) => void;
}

const validationSchema = yup.object({
  text: yup.string().required("Please enter comment"),
});

const AddComment: FC<Props> = ({ onSubmit }) => {
  const commentForm = useFormik({
    initialValues: {
      text: "",
    },
    validationSchema,
    onSubmit: (values) => {
      onSubmit(values.text);
    },
  });
  return (
    <Box>
      <form
        style={{
          display: "flex",
          gap: "10px",
          marginLeft: "15px",
          justifyContent: "center",
          marginTop: "10px",
        }}
        onSubmit={commentForm.handleSubmit}
      >
        <TextField
          id='text'
          name='text'
          placeholder='Enter text'
          label='text'
          size='small'
          variant='outlined'
          value={commentForm.values.text}
          onChange={commentForm.handleChange}
          onBlur={commentForm.handleBlur}
          error={commentForm.touched.text && Boolean(commentForm.errors.text)}
          helperText={commentForm.errors.text}
        />
        <Box sx={{ marginY: "auto" }}>
          <Button variant='contained' type='submit'>
            Add comment
          </Button>
        </Box>
      </form>
    </Box>
  );
};

export default AddComment;
