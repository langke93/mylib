 'use strict';
 const {
     CompositeDecorator,
     ContentState,
     Editor,
     EditorState,
     convertFromHTML,
     convertToRaw
   } = Draft;
var fun1 = function (sampleMarkup) {
	const blocksFromHTML = Draft.convertFromHTML(sampleMarkup);
	return blocksFromHTML;
	//console.log(blocksFromHTML);
};
 
//print("String :"+Object.prototype.toString("aaaa"));

