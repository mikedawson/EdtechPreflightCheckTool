package net.mike_dawson.edtechpreflightchecktool.viewmodel

/**
 * This is not pretty, but it works using @JsFun by which Kotlin will make a JS function
 * accessible to wasm code.
 *
 * https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-js-fun/
 *
 * "This is a temporary annotation because K/Wasm <-> JS interop is not designed yet."
 */
@JsFun("""
    (filename, content) => {
        // Create a blob containing the text content
        const blob = new Blob([content], { type: 'text/plain;charset=utf-8' });
        
        // Generate an object URL pointing to the blob
        const url = URL.createObjectURL(blob);
        
        // Create a temporary anchor element and trigger a click
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        
        // Clean up
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }
""")
external fun jsSaveTextFile(filename: String, content: String)

actual fun saveTextFile(name: String, text: String) {
    jsSaveTextFile(filename = name, content = text)
}

@JsFun("""
    (onContentRead) => {
        // Create an off-screen file input element
        const input = document.createElement('input');
        input.type = 'file';
        
        // Optional: specify file formats you want to filter (e.g. '.json, .txt')
        input.accept = '.json, .txt, *'; 
        
        input.onchange = (event) => {
            const files = event.target.files;
            if (!files || files.length === 0) return;
            
            const file = files[0];
            const reader = new FileReader();
            
            reader.onload = (e) => {
                const text = e.target.result;
                // Invoke the Kotlin callback with the file contents
                onContentRead(text);
            };
            
            reader.readAsText(file);
        };
        
        // Programmatically trigger the file dialog
        input.click();
    }
""")
external fun wasmJsOpenText(
    onContentRead: (String) -> Unit
)

actual fun openTextFile(onContentRead: (String) -> Unit) {
    wasmJsOpenText(onContentRead)
}
