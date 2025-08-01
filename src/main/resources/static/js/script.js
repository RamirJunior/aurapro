
const fileUpload = document.getElementById('file-upload');
const fileInfo = document.getElementById('file-info');
const fileNameSpan = document.getElementById('file-name');
const uploadBtn = document.getElementById('upload-btn');
const downloadBtn = document.getElementById('download-btn');
const copyBtn = document.getElementById('copy-btn');
const summaryBox = document.getElementById('summary-box');
const toast = document.getElementById('toast');
const fileError = document.getElementById('file-error');
const loader = document.getElementById('loader');
const summaryTitle = document.getElementById('summary-title');

const acceptedTypes = ['audio/mpeg', 'audio/mp4', 'audio/x-m4a', 'audio/wav'];

function updateFileDisplay(file) {
  const isValid = acceptedTypes.includes(file.type);
  fileInfo.style.display = 'flex';
  fileNameSpan.textContent = file.name.length > 30 ? file.name.slice(0, 27) + '...' : file.name;
  fileNameSpan.title = file.name;
  fileInfo.className = 'file-info ' + (isValid ? 'valid' : 'invalid');
  fileError.style.display = isValid ? 'none' : 'block';
  fileError.textContent = isValid ? '' : 'Formatos aceitos: mp3, m4a, wav';
  uploadBtn.disabled = !isValid;
  if (!isValid) {
    uploadBtn.innerText = 'Criar Resumo';
  }
}

fileUpload.addEventListener('change', () => {
  if (fileUpload.files.length > 0) {
    updateFileDisplay(fileUpload.files[0]);
  }
});

function handleDrop(event) {
  event.preventDefault();
  const droppedFile = event.dataTransfer.files[0];
  if (droppedFile) {
    fileUpload.files = event.dataTransfer.files;
    updateFileDisplay(droppedFile);
  }
}

function removeFile() {
  fileUpload.value = '';
  fileInfo.style.display = 'none';
  fileError.style.display = 'none';
  uploadBtn.disabled = true;
  fileNameSpan.textContent = '';
  fileInfo.className = 'file-info';
  uploadBtn.innerText = 'Criar Resumo';
}

uploadBtn.addEventListener('click', () => {
  const file = fileUpload.files[0];
  if (!file || !acceptedTypes.includes(file.type)) return;

  uploadBtn.disabled = true;
  loader.style.display = 'inline-block';
  summaryBox.innerText = '';
  summaryTitle.innerText = `Processando: ${file.name}`;
  downloadBtn.disabled = true;
  copyBtn.disabled = true;

  const formData = new FormData();
  formData.append('audioFile', file);

  fetch('http://localhost:8080/aurapro/process', {
    method: 'POST',
    body: formData
  })
  .then(res => res.json())
  .then(data => {
    setTimeout(() => {
      fileInfo.className = 'file-info processed';
      uploadBtn.disabled = false;
      uploadBtn.innerText = 'Refazer';
      loader.style.display = 'none';
      copyBtn.disabled = false;
      downloadBtn.disabled = false;

      summaryBox.innerText = data.summary;
      summaryTitle.innerText = `Resumo do áudio: ${file.name}`;

      downloadBtn.onclick = () => {
        const blob = new Blob([data.transcription], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = file.name.replace(/\.[^/.]+$/, '') + '_transcription.txt';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
      };
    }, 6000); // Delay de 6 segundos
  })
  .catch(() => {
    loader.style.display = 'none';
    uploadBtn.disabled = false;
    alert('Erro ao processar o áudio. Tente novamente.');
  });
});

copyBtn.addEventListener('click', () => {
  const text = summaryBox.innerText.trim();
  if (!text) return;

  navigator.clipboard.writeText(text).then(() => {
    toast.classList.add('show');
    setTimeout(() => toast.classList.remove('show'), 3000);
  });
});
