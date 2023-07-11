import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssFileParser } from '../iss-file-parser.model';

@Component({
  selector: 'jhi-iss-file-parser-detail',
  templateUrl: './iss-file-parser-detail.component.html',
})
export class IssFileParserDetailComponent implements OnInit {
  issFileParser: IIssFileParser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issFileParser }) => {
      this.issFileParser = issFileParser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
