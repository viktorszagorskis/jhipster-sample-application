import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBriefing } from 'app/shared/model/briefing.model';

@Component({
  selector: 'jhi-briefing-detail',
  templateUrl: './briefing-detail.component.html'
})
export class BriefingDetailComponent implements OnInit {
  briefing: IBriefing | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ briefing }) => {
      this.briefing = briefing;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
