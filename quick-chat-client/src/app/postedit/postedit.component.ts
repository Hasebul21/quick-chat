import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-postedit',
  imports: [FormsModule],
  templateUrl: './postedit.component.html',
  styleUrl: './postedit.component.scss'
})
export class PosteditComponent {
  post: any;
  constructor(private dialogRef: MatDialogRef<PosteditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.post = Object.assign({}, data);
    console.log(this.post);
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this.dialogRef.close(this.post);
  }


}

