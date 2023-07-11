import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssPortalFile, NewIssPortalFile } from '../iss-portal-file.model';

export type PartialUpdateIssPortalFile = Partial<IIssPortalFile> & Pick<IIssPortalFile, 'id'>;

type RestOf<T extends IIssPortalFile | NewIssPortalFile> = Omit<T, 'fromDisbursementDate' | 'toDisbursementDate'> & {
  fromDisbursementDate?: string | null;
  toDisbursementDate?: string | null;
};

export type RestIssPortalFile = RestOf<IIssPortalFile>;

export type NewRestIssPortalFile = RestOf<NewIssPortalFile>;

export type PartialUpdateRestIssPortalFile = RestOf<PartialUpdateIssPortalFile>;

export type EntityResponseType = HttpResponse<IIssPortalFile>;
export type EntityArrayResponseType = HttpResponse<IIssPortalFile[]>;

@Injectable({ providedIn: 'root' })
export class IssPortalFileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/iss-portal-files');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issPortalFile: NewIssPortalFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issPortalFile);
    return this.http
      .post<RestIssPortalFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(issPortalFile: IIssPortalFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issPortalFile);
    return this.http
      .put<RestIssPortalFile>(`${this.resourceUrl}/${this.getIssPortalFileIdentifier(issPortalFile)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(issPortalFile: PartialUpdateIssPortalFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issPortalFile);
    return this.http
      .patch<RestIssPortalFile>(`${this.resourceUrl}/${this.getIssPortalFileIdentifier(issPortalFile)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestIssPortalFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestIssPortalFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssPortalFileIdentifier(issPortalFile: Pick<IIssPortalFile, 'id'>): number {
    return issPortalFile.id;
  }

  compareIssPortalFile(o1: Pick<IIssPortalFile, 'id'> | null, o2: Pick<IIssPortalFile, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssPortalFileIdentifier(o1) === this.getIssPortalFileIdentifier(o2) : o1 === o2;
  }

  addIssPortalFileToCollectionIfMissing<Type extends Pick<IIssPortalFile, 'id'>>(
    issPortalFileCollection: Type[],
    ...issPortalFilesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issPortalFiles: Type[] = issPortalFilesToCheck.filter(isPresent);
    if (issPortalFiles.length > 0) {
      const issPortalFileCollectionIdentifiers = issPortalFileCollection.map(
        issPortalFileItem => this.getIssPortalFileIdentifier(issPortalFileItem)!
      );
      const issPortalFilesToAdd = issPortalFiles.filter(issPortalFileItem => {
        const issPortalFileIdentifier = this.getIssPortalFileIdentifier(issPortalFileItem);
        if (issPortalFileCollectionIdentifiers.includes(issPortalFileIdentifier)) {
          return false;
        }
        issPortalFileCollectionIdentifiers.push(issPortalFileIdentifier);
        return true;
      });
      return [...issPortalFilesToAdd, ...issPortalFileCollection];
    }
    return issPortalFileCollection;
  }

  protected convertDateFromClient<T extends IIssPortalFile | NewIssPortalFile | PartialUpdateIssPortalFile>(issPortalFile: T): RestOf<T> {
    return {
      ...issPortalFile,
      fromDisbursementDate: issPortalFile.fromDisbursementDate?.format(DATE_FORMAT) ?? null,
      toDisbursementDate: issPortalFile.toDisbursementDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restIssPortalFile: RestIssPortalFile): IIssPortalFile {
    return {
      ...restIssPortalFile,
      fromDisbursementDate: restIssPortalFile.fromDisbursementDate ? dayjs(restIssPortalFile.fromDisbursementDate) : undefined,
      toDisbursementDate: restIssPortalFile.toDisbursementDate ? dayjs(restIssPortalFile.toDisbursementDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestIssPortalFile>): HttpResponse<IIssPortalFile> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestIssPortalFile[]>): HttpResponse<IIssPortalFile[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
