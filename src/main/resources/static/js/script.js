const audioInput = document.getElementById('audioFile');
const removeFileBtn = document.getElementById('removeFileBtn');
const submitBtn = document.getElementById('submitBtn');
const loadingDiv = document.getElementById('loading');
const resultContainer = document.getElementById('resultContainer');
const transcriptionText = document.getElementById('transcriptionText');
const summarizeSwitch = document.getElementById('summarizeSwitch');
const downloadLink = document.getElementById('downloadLink');
const toast = document.getElementById('toast');

audioInput.addEventListener('change', () => {
    const file = audioInput.files[0];
    const allowedTypes = ['audio/mpeg', 'audio/wav', 'audio/x-m4a', 'audio/mp4'];
    if (file && allowedTypes.includes(file.type)) {
        submitBtn.disabled = false;
        removeFileBtn.classList.remove('hidden');
    } else {
        alert("Por favor, selecione um arquivo de áudio válido (mp3, wav ou m4a).");
        audioInput.value = "";
        submitBtn.disabled = true;
        removeFileBtn.classList.add('hidden');
    }
});

removeFileBtn.addEventListener('click', () => {
    audioInput.value = "";
    submitBtn.disabled = true;
    removeFileBtn.classList.add('hidden');
});

document.getElementById('audioForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const file = audioInput.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('audioFile', file);
    formData.append('summarize', summarizeSwitch.checked);

    submitBtn.disabled = true;
    audioInput.disabled = true;
    loadingDiv.classList.remove('hidden');
    resultContainer.classList.add('hidden');
    transcriptionText.textContent = "";

    setTimeout(() => {
        fetch('http://localhost:8080/aurapro/process', {
            method: 'POST',
            body: formData
        })
        .then(res => res.json())
        .then(data => {
            transcriptionText.textContent = data.transcription || "Nenhuma transcrição retornada.";
            resultContainer.classList.remove('hidden');

            const hasSummary = data.summary && data.summary.trim() !== "";
            if (summarizeSwitch.checked && hasSummary && data.transcription.trim() !== "") {
                const blob = new Blob([data.summary], { type: 'text/plain' });
                const url = URL.createObjectURL(blob);
                downloadLink.href = url;
                downloadLink.disabled = false;
            } else {
                downloadLink.disabled = true;
            }
        })
        .catch(err => {
            alert("Erro ao processar o áudio.");
            console.error(err);
        })
        .finally(() => {
            loadingDiv.classList.add('hidden');
            submitBtn.disabled = false;
            audioInput.disabled = false;
        });
    }, 5000); // Delay de simulação
});

document.getElementById("copyTranscriptionBtn").addEventListener("click", () => {
    const transcription = transcriptionText.innerText;
    if (transcription.trim() !== "") {
        navigator.clipboard.writeText(transcription).then(() => {
            toast.classList.add("show");
            setTimeout(() => {
                toast.classList.remove("show");
            }, 3000);
        });
    }
});


