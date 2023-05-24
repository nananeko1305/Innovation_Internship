<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
  $file = $_FILES['file'];
  $destination = '../src/assets/images/' . $file['name'];

  if (move_uploaded_file($file['tmp_name'], $destination)) {
    echo json_encode(['message' => 'File saved successfully!']);
  } else {
    echo json_encode(['error' => 'Error saving file!']);
  }
}
?>
