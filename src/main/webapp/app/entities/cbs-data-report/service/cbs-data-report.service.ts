import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbsDataReport, NewCbsDataReport } from '../cbs-data-report.model';

export type PartialUpdateCbsDataReport = Partial<ICbsDataReport> & Pick<ICbsDataReport, 'id'>;

export type EntityResponseType = HttpResponse<ICbsDataReport>;
export type EntityArrayResponseType = HttpResponse<ICbsDataReport[]>;

@Injectable({ providedIn: 'root' })
export class CbsDataReportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cbs-data-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cbsDataReport: NewCbsDataReport): Observable<EntityResponseType> {
    return this.http.post<ICbsDataReport>(this.resourceUrl, cbsDataReport, { observe: 'response' });
  }

  update(cbsDataReport: ICbsDataReport): Observable<EntityResponseType> {
    return this.http.put<ICbsDataReport>(`${this.resourceUrl}/${this.getCbsDataReportIdentifier(cbsDataReport)}`, cbsDataReport, {
      observe: 'response',
    });
  }

  partialUpdate(cbsDataReport: PartialUpdateCbsDataReport): Observable<EntityResponseType> {
    return this.http.patch<ICbsDataReport>(`${this.resourceUrl}/${this.getCbsDataReportIdentifier(cbsDataReport)}`, cbsDataReport, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbsDataReport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbsDataReport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbsDataReportIdentifier(cbsDataReport: Pick<ICbsDataReport, 'id'>): number {
    return cbsDataReport.id;
  }

  compareCbsDataReport(o1: Pick<ICbsDataReport, 'id'> | null, o2: Pick<ICbsDataReport, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbsDataReportIdentifier(o1) === this.getCbsDataReportIdentifier(o2) : o1 === o2;
  }

  addCbsDataReportToCollectionIfMissing<Type extends Pick<ICbsDataReport, 'id'>>(
    cbsDataReportCollection: Type[],
    ...cbsDataReportsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbsDataReports: Type[] = cbsDataReportsToCheck.filter(isPresent);
    if (cbsDataReports.length > 0) {
      const cbsDataReportCollectionIdentifiers = cbsDataReportCollection.map(
        cbsDataReportItem => this.getCbsDataReportIdentifier(cbsDataReportItem)!
      );
      const cbsDataReportsToAdd = cbsDataReports.filter(cbsDataReportItem => {
        const cbsDataReportIdentifier = this.getCbsDataReportIdentifier(cbsDataReportItem);
        if (cbsDataReportCollectionIdentifiers.includes(cbsDataReportIdentifier)) {
          return false;
        }
        cbsDataReportCollectionIdentifiers.push(cbsDataReportIdentifier);
        return true;
      });
      return [...cbsDataReportsToAdd, ...cbsDataReportCollection];
    }
    return cbsDataReportCollection;
  }
}
